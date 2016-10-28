
import groovyx.net.http.HTTPBuilder
import org.apache.http.client.utils.URIBuilder

import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.RESTClient

class XLTestViewClient {
  final RESTClient client

  XLTestViewClient(String url, String user, String password) {
    this.client = new RESTClient(url)
    def userPassBase64 = "$user:$password".toString().bytes.encodeBase64()
    client.setHeaders([
      'Authorization': "Basic $userPassBase64",
      'Accept': 'application/json; charset=UTF-8',
    ])
  }

  def createProject(projectName) {
    def response = client.post(path:'projects', body: [title: projectName.toString()], contentType: JSON)
    if( response.status == 201 ) {
      return response.data.id
    } else {
      throw new RuntimeException("Failed to create ${projectName} status=${response.status} data=${response.data}")
    }
  }

  def getOrCreateProject(projectName) {
    def response = client.get(path:'projects', query:[title: projectName.toString()], contentType: JSON)
    if( response.status == 200 ) {
       if ( response.data.size() == 1 ) {
          response.data[0].id
       } else if ( response.data.size() > 1 ) {
          throw new RuntimeException("Multiple projects found with the same name ${projectName}")
       } else {
          createProject(projectName)
       }
    } else {
       throw new RuntimeException("Failed to lookup/create ${projectName}")
    }
  }

  def createTestSpecification(projectId, testSpecificationName, testTool) {
    println "ts: $testSpecificationName"
    def response = client.post(path:"projects/$projectId/testspecifications", body: [title: testSpecificationName.toString(), testToolName: testTool.toString()], contentType: JSON)
    if( response.status == 201 ) {
      return response.data.id
    } else {
      throw new RuntimeException("Failed to create ${projectName} status=${response.status} data=${response.data}")
    }
  }

  def getOrCreateTestSpecification(projectId, testSpecificationName, testTool = 'xlt.JUnit') {
    def response = client.get(path:"projects/$projectId/testspecifications", query:[title: testSpecificationName.toString()], contentType: JSON)
    if( response.status == 200 ) {
       if ( response.data.size() == 1 ) {
          response.data[0].id
       } else if ( response.data.size() > 1 ) {
          throw new RuntimeException("Multiple test specifications found with the same name ${testSpecificationName}")
       } else {
          createTestSpecification(projectId, testSpecificationName, testTool)
       }
    } else {
       throw new RuntimeException("Failed to lookup/create ${testSpecificationName}")
    }
  }
}
