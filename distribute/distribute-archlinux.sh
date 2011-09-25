#!/bin/sh

# This file is based on
# pacinstall 0.1 by Andreas Textor (texray@gmx.de)
# which script is based on inst2rpm by Jon A. Christopher,
# which is included with installwatch until version 0.5.6

echo "Determining Version:"
VERSION=$(cat ../src/com/t_oster/visicut/gui/resources/VisicutApp.properties |grep Application.version)
VERSION=${VERSION#*=}
VERSION=${VERSION// /}
echo "Version is: \"$VERSION\""

# check for parameters and id
if [ "$1" = "-h" ] || [ "$1" = "--help" ] || [ "$1" = "help" ]; then
  displayhelp
  exit
fi

if [ `whoami` != "root" ]; then
  echo "WARNING! You are not root. Errors during the installition are expected."
  echo -n "If you still want to continue, type "yes" (otherwise press CTRL+C): "
  read tmp
  if [ x"$tmp" != "xyes" ]; then
    exit
  fi
fi


# check for installwatch
INSTALLWATCH=`which installwatch 2>/dev/null`
if [ x$INSTALLWATCH = x ]; then
  echo "installwatch not found in PATH, exiting."
  echo "Get it from: http://asic-linux.com.mx/~izto/checkinstall/installwatch.html"
  exit
fi


# set up values and temp-directory
MPWD="`pwd`"
a="${MPWD##*/}"
PACKAGE="visicut"
URL="http://hci.rwth-aachen.de/visicut"
SUMMARY="A userfriendly tool to create, save and send Jobs to a Lasercutter"
LICENSE="LGPL"
ARCH="any"
DEPENDS="java-runtime"
FAKEROOT=/tmp/pacinstall
PKGINFO=$FAKEROOT/.PKGINFO
FILEINFO=$FAKEROOT/.FILELIST
INSTALLWATCHLOG=/tmp/installwatchlog.$$
FILELIST=/tmp/filelist.$$

if [ -d $FAKEROOT ]; then
  rm -rf $FAKEROOT
fi
mkdir $FAKEROOT

echo
echo "Installing using installwatch..."
cd ..
installwatch -o $INSTALLWATCHLOG make install -e PREFIX=/usr

echo "Copying files to temp directory..."

awk '($2=="open"||$2=="link") && $4=="#success" {print $3} ;($2=="open"||$2=="link") && $4!="#success" {print "$3 $4 $5"} ; $2=="rename" {print $4}' < $INSTALLWATCHLOG | grep ^/ | \
   egrep -v '/dev|$HOME' | sort | uniq > $FILELIST

for i in `cat $FILELIST`; do
 tdir="${FAKEROOT}/${i%/*}"
 mkdir -p "$tdir"
 cp "$i" "$tdir"
done
/bin/rm $FILELIST

echo "Create PKGINFO and FILEINFO..."

# write PKGINFO file
[ -e $PKGINFO ] && rm $PKGINFO
cat > $PKGINFO << EOF
# Generated by pacinstall
pkgname = $PACKAGE
pkgver = $VERSION
pkgdesc = $SUMMARY
url = $URL
builddate = `LC_ALL=C date "+%a %b %d %H:%M:%S %Y"`
packager = Pacinstall
size = `/bin/du -sb $FAKEROOT|awk '{ print $1 }'`
arch = $ARCH
license = $LICENSE
EOF
if [ x"$DEPENDS" != x ]; then
  for i in $DEPENDS; do
    echo "depend = $i" >> $PKGINFO
  done
fi

# write FILEINFO file
cd $FAKEROOT
[ -e $FILEINFO ] && rm $FILEINFO
for i in *; do
  find $i >> $FILEINFO
done

PKG="$MPWD/$PACKAGE-$VERSION.pkg.tar.gz"
echo "Creating Package `basename $PKG`..."

cd "$FAKEROOT"
tar cfvz "$PKG" .PKGINFO .FILELIST *
echo "$PKG written."
# clean up
/bin/rm -rf $FAKEROOT
/bin/rm $INSTALLWATCHLOG

