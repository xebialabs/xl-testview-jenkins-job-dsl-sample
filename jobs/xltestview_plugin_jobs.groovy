
def client = new XLTestViewClient('http://localhost:6516/api/v1/', "${XLTV_USER}", "${XLTV_PASSWORD}")

def jobName = 'XL TestView Jenkins Plugin'

def projectId = client.getOrCreateProject('Demo')
def testSpecId = client.getOrCreateTestSpecification(projectId, "Test specification for $jobName")

job(jobName) {
  scm {
    git {
      remote {
        github('jenkinsci/xltestview-plugin')
      }
    }
    steps {
      gradle('build')
    }
    configure { project ->
      project / publishers / 'com.xebialabs.xlt.ci.XLTestView' << {
        testSpecifications {
          'com.xebialabs.xlt.ci.TestSpecificationDescribable' {
            testSpecificationId(testSpecId)
            includes('build/test-results/**/TEST*.xml')
          }
        }
      }
    }
  }
}
