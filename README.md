jhyun-jsptags
=============


Marvelous JSP Taglib!!!


* `capture` tag
  * capture enclosing JSP block as JSP attribute.
  * .. simple-as-fun *layout*, *template inheritance*. 
  
* `eval-only-once` tag
  * evaluate enclosing JSP block only once in request-scope.
  * .. to prevent specific JSP code-blocks evaluate multiple times. (even with includes.) 
  
* `cached` tag
  * javax.cache-backed.
  * cache enclosing JSP block.
  * .. show it's contents with cached(if-evaluated-once) contents.


Examples, Maven Repository.
--------------------------- 

* Examples: https://github.com/ageldama/jhyun-jsptags-hello

* Deployed Maven Repository: http://nexus.inger.co.kr/content/groups/public/

		<repositories>
			<!-- inger.co.kr -->
			<repository>
				<releases>
					<enabled>true</enabled>
					<updatePolicy>always</updatePolicy>
					<checksumPolicy>warn</checksumPolicy>
				</releases>
				<snapshots>
					<enabled>true</enabled>
					<updatePolicy>never</updatePolicy>
					<checksumPolicy>fail</checksumPolicy>
				</snapshots>
				<id>ingerPublic</id>
				<name>inger.co.kr public</name>
				<url>http://nexus.inger.co.kr/content/groups/public/</url>
				<layout>default</layout>
			</repository>
		</repositories>

