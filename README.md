SpiderCache
=====================

SpiderCache is an automatically scaling distributed web cache designed for saving bandwidth 
in low-bandwidth environments. 

The software is designed to save bandwidth for a wide-area network of literacy centers as described in
[1]. The group requesting the creation of this software was the Liberian Literacy Foundation, whose website can
be found at [2]. 

SpiderCache can essentially be broken up into 3 Components:

Distributed Key-Value Store
DNS Intercept
Caching

The distributed key-value store aspect of SpiderCache provides both scalability and redundancy.
This is essential for the users, who run a higher risk of power outages.

The DNS Intercept portion of the software translates normal end user web requests into keys for the distributed
key-value store. Some URLs and some types of content cannot be stored on the network, such as secure (https) content
and streaming videos. 

The caching aspect of the software prioritizes popular content so that it will be more available on the network
than content that has just recently. This is done by eliminating the least recently used (LRU) content that is
cached on a given node when material must be evicted. It's important to note that content has an arbitrary period
after which it is considered "stale".

Typical Use Scenario
=========================
 
 To give a complete picture, here's a narrative of how a typical use of SpiderCache works:
 
 To begin, the software is running on a wide area network of "learning centers". Some of these centers have
 direct access to the internet, but most do not. They are all connected to each other in a wireless and optical
 mesh network. Every literacy center has one machine running an instance of SpiderCache.
 
 A user with a thin client running a web browser connects to the local area network of one of these centers 
 and requests http://www.google.com/. The DNS request for Google.com is answered by the local SpiderCache node, 
 who replies by telling the user that it is google.com. The HTTP request that is sent to the local spidercache 
 node as a result is then searched for as a key in the local SpiderCache node's cache. If the key corresponding to
 http://www.google.com is not found, the distributed key-value store is queried. If the content exists in the DHT,
 that material is sent to the node requesting it, who then caches the material and serves it to the user as web server.
 
 If the material is not found in the Distributed Hash Table, a request is sent to the next available node that is 
 known to have an internet connection. This Gateway node queues the request and, when there is bandwidth, makes a request
 to the internet for the content. After the material is retrieved, it is sent back to the requesting node, being 
 cached by other nodes as it makes its way back to the orignal requesting node. It's important to note here that if 
 the material uses HTTPS, it is not cached by any node but rather is relayed to the original requesting SpiderCache node.
 
 When the material has made it back to the requesting node, it is delivered to the thin client that made the request
 for that URL. Now if anybody else would like the recent page at http://www.google.com/, they may get it much faster
 and with a higher degree of reliability than would have otherwise been possible.
 
 
 Remaining Work
 ==========================
 SpiderCache's main source of security is currently provided by the assumption that end users will not have access
 to the software themselves. This is not acceptable. A cryptographically based system of trust needs to be created
 to provide more reasonable security for this network.
 
 
 [1] http://eng.utoledo.edu/~mgrube/Proposal.pdf
 [2] http://www.liberianbooks.org
 
