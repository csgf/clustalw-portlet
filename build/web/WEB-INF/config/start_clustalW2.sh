#!/bin/sh

ARGS="$2"

echo;echo "- Running ClustalW2 (v2.0.11) on "`hostname -f`
chmod +x ${PWD}/clustalw2
./clustalw2 -INFILE=`basename $1` "${ARGS}"

echo "- Packing output files ..."
tar zcvf outputs.tar.gz *.aln *.dnd
#tar zcvf outputs.tar.gz --exclude='*clustal*' --exclude='output.README' --exclude='std.*' *

cat <<EOF >> output.README
#
# README - ClustalW2 (v2.0.11) Multiple Sequence Alignments
#
# Giuseppe LA ROCCA, INFN Catania
# <mailto:giuseppe.larocca@ct.infn.it>
#
This application allows Science Gateway users to performs the Multiple Sequence Alignment (ClustalW2) for DNA or proteins.
It attempts to calculate the best match for the selected sequences, and lines them up so that the identities, similarities 
and differences can be seen.

If the job has been successfully executed, the following files will be produced:
~ std.out:           the standard output file;
~ std.err:           the standard error file;
~ outputs.tar.gz:    the results of the Multiple Sequence Alignment.
EOF
