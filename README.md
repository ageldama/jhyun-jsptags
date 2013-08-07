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

* Examples: https://github.com/ageldama/jhyun-jsptags-hello
