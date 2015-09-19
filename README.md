Echo
====

<br>should_work = 'https://www.youtube.com/watch?v=shfjgRwNcWk'
<br>http://stackoverflow.com/questions/9611397/how-to-extract-closed-caption-transcript-from-youtube-video
<br>http://www.youtube.com/api/timedtext?v=zzfCVBSsvqA&lang=en
<br>http://mo.dbxdb.com/Yang/
<br>limit youtube search to deliberate closed-captions:
<br>https://www.youtube.com/results?search_query=david+bowie%2C+cc
<br>http://www.serpsite.com/youtube-subtitles-download-tool/
<br>http://downsub.com/
<br>http://code.google.com/p/gcap/
<br>youtube-dl --write-auto-sub  --skip-download  https://www.youtube.com/watch?v=v9L4qL_XKcs
<br>youtube-dl --list-subs http://www.youtube.com/watch?v=X6p5AZp7r_Q
<br>just need to filter by youtube videos that have CC's already


https://video.google.com/timedtext?lang=en&v=X6p5AZp7r_Q
can be srt: http://video.google.com/timedtext?lang=en&v=yJXTXN4xrI8&fmt=srt
captions language list:
https://video.google.com/timedtext?hl=en&type=list&v=X6p5AZp7r_Q
e.g. lang_code="en" 

search for captions:
q=<words>, cc

e.g. q="sesame street, cc"

it looks like youtube_search in yt.py works.


