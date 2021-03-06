#!/bin/bash
#
# Copyright (c) 2017, RTE (http://www.rte-france.com)
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.
#

installBinDir=$(dirname $(readlink -f $0))
installDir=${installBinDir%/*}

. $installDir/etc/itesla.conf

if [[ -n "$OMPI_MCA_rmaps_base_cpus_per_rank" ]]; then
	CORES=$OMPI_MCA_rmaps_base_cpus_per_rank
else
   	CORES=1
fi

tmpdir=$HOME/tmp 
mkdir $tmpdir > /dev/null 2>&1

export CLASSPATH=$installDir/share/java/*:$CLASSPATH
rank=$OMPI_COMM_WORLD_RANK
if [ $rank = 0 ]; then
    if [ -z $JAVA_HOME ]; then
        echo "JAVA_HOME is not defined"
        exit -1
    fi
    export LD_PRELOAD=libmpi.so
    export LD_LIBRARY_PATH=${installDir}/lib:$LD_LIBRARY_PATH
    [ -n "$itesla_cache_dir" ] && options+="-Ditesla.cache.dir=$itesla_cache_dir"
    [ -n "$itesla_config_dir" ] && options+=" -Ditesla.config.dir=$itesla_config_dir"
    [ -n "$itesla_config_name" ] && options+=" -Ditesla.config.name=$itesla_config_name"
    options+=" -Dlogback.configurationFile="
    [ -f "$itesla_config_dir/logback-itools.xml" ] && options+="$itesla_config_dir" || options+="$installDir/etc"
    options+="/logback-itools.xml"
    [ -z "$java_xmx" ] && java_xmx=8G
    $JAVA_HOME/bin/java \
-Xmx$java_xmx \
-verbose:gc -XX:+PrintGCTimeStamps -Xloggc:$tmpdir/gc.log \
$options \
eu.itesla_project.computation.mpi.MpiMaster \
"$@" \
--tmp-dir=$tmpdir \
--statistics-db-dir=$HOME \
--statistics-db-name="statistics" \
--cores=$CORES \
--stdout-archive=$tmpdir/stdout-archive.zip
else
    mkdir $HOME/archive > /dev/null 2>&1
    rm -r $tmpdir/itesla_common_${rank}* > /dev/null 2>&1
    rm -r $tmpdir/itesla_job_${rank}* > /dev/null 2>&1
    rm -r $tmpdir/itesla_work_${rank}* > /dev/null 2>&1
    ${installDir}/bin/slave --tmp-dir=$tmpdir --archive-dir=$HOME/archive --log-file=$tmpdir/slave.log --cores=$CORES
fi
