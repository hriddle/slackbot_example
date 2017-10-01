#Pipeline 

1. Install [bosh CLI](https://bosh.io/docs/cli-v2.html#install)
1. Download `concourse-lite.yml` from the [latest Concourse release](https://github.com/concourse/concourse/releases/tag/v3.5.0)
1. Run `bosh create-env concourse-lite.yml` in the directory where the manifest was saved
    - This will take a little bit
1. Once finished, check [http://192.168.100.4:8080/](http://192.168.100.4:8080/), Concourse web server should be running
1. Change directory to `cd ~/xpbot/ci`
1. Set fly to target your local concourse `fly -t xpbot login -c http://192.168.100.4:8080`
1. Set the pipeline by running the following `fly -t xpbot set-pipeline --config pipeline.yml --pipeline xpbot-pipeline`