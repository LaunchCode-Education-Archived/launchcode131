#/bin/bash

jekyll b
cp -r /home/warehouse/dshook/launchcode131/webpage/_site/* /home/warehouse/dshook/launchcode131-stlouis
cd /home/warehouse/dshook/launchcode131-stlouis
git checkout -b gh-pages
git add *
git commit -m "updating gh-pages site"
git push origin gh-pages
