# Hashing

I used the Lempel Ziv compression to compress text. The example text I looked at was a string of 1s and 0s, or A's and B's at one point, like example from the YouTube video we were recommended to look at as a resource.

I tested using strings of different lengths to figure out when I would yield benefits from the compression. Only after I tripled the size of the input string, essenitally just concatenating the string with itself three times, did I see compression and not expansion as a result of using Lempel Ziv.

Here is my output from the testing:

![1](https://github.com/jes-bro/hashing/blob/master/Screenshot%20from%202024-04-08%2000-52-05.png)

![2](https://someurl/imagelocation/image.png)

![3](https://someurl/imagelocation/image.png)

![4](https://someurl/imagelocation/image.png)
