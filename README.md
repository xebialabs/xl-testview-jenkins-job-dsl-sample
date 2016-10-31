# Configuring Jenkins Jobs and XL TestView with the Jenkins Job DSL 

This is an example project demonstrating a way to configure both Jenkins Jobs and XL TestView from the Jenkins Job DSL.

## Prerequisites

* a recent version of Jenkins
* an instance of XL TestView (1.4 or better).

The demonstration code assumes that both are running on the local system with default ports.

Install the following plugins in your Jenkins instance:

* The [XL TestView Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+TestView+Plugin).
* The [Jenkins Job DSL Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Job+DSL+Plugin).
* The [Gradle Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Gradle+Plugin).
* The [Github Plugin](https://wiki.jenkins-ci.org/display/JENKINS/GitHub+Plugin).
* The [Mask Passwords Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Mask+Passwords+Plugin).

## DSL

Configure a job that executes the Job DSL.

1. Go to *Manage Jenkins*, *Configure System*, *Mask Passwords - Global name/password pairs* and add two entries `XLTV_USER` with value admin, and `XLTV_PASSWORD` with value 'admin' (if necessary adapt to your setup).
2. Also configure the XL TestView server and ensure the connection test works.
2. Create a new *Freestyle job*. Name it 'Job Creator'.
3. Select git in *Source code Management*
4. Use https://github.com/xebialabs/xl-testview-jenkins-job-dsl-sample.git as repository URL.
5. Enable *Mask passwords (and enable global passwords)* in *Build Environment*
6. Add a *Invoke gradle step*, Select *Use gradle wrapper* and specify `libs` for tasks.
7. Add a *Process Job DSLs* step to it. Select *Look on Filesystem* option. And add `jobs/*_jobs.groovy` as DSL scripts pattern. 
8. Press safe.

Once this is done you can execute the 'Job Creator' job. This will result in a new Jenkins Job that builds the XL TestView Jenkins plugin. The job will be configured to upload its test results to the XL TestView instance.
