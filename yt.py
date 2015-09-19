#!/usr/bin/python

# Usage example:
# python captions.py --videoid='<video_id>' --name='<name>' --file='<file>' --language='<language>' --action='action'

import httplib2
import os
import sys

from apiclient.discovery import build_from_document
from apiclient.errors import HttpError
from oauth2client.client import flow_from_clientsecrets
from oauth2client.file import Storage
from oauth2client.tools import argparser, run_flow
 


from apiclient.discovery import build
from apiclient.errors import HttpError
from oauth2client.tools import argparser


# Set DEVELOPER_KEY to the API key value from the APIs & auth > Registered apps
# tab of
#   https://cloud.google.com/console
# Please ensure that you have enabled the YouTube Data API for your project.
DEVELOPER_KEY="AIzaSyCMJUwbh2KMuvZHlfbuIb8sAfQUyPMzMYg"
YOUTUBE_API_SERVICE_NAME = "youtube"
YOUTUBE_API_VERSION = "v3"

youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION, developerKey=DEVELOPER_KEY)


def get_authenticated_service(arg):
  flow = flow_from_clientsecrets(CLIENT_SECRETS_FILE, scope=YOUTUBE_READ_WRITE_SSL_SCOPE,
    message=MISSING_CLIENT_SECRETS_MESSAGE)

  storage = Storage("%s-oauth2.json" % arg)
  credentials = storage.get()

#  if credentials is None or credentials.invalid:
#    credentials = run_flow(flow, storage, args)

  # Trusted testers can download this discovery document from the developers page
  # and it should be in the same directory with the code.
  with open("youtube-v3-api-captions.json", "r") as f:
    doc = f.read()
    return build_from_document(doc, http=credentials.authorize(httplib2.Http()))


#cap_url = " http://video.google.com/timedtext?lang={LANG}&v={VIDEOID}"
def search (q, location="37.42307,-122.08427", location_radius="5km", max_results=10):
    search_response = youtube.search().list(
      q=q,
      type="video",
      location=location,
      locationRadius=location_radius,
      part="id,snippet",
      maxResults=max_results
    ).execute()
    return search_response

MISSING_CLIENT_SECRETS_MESSAGE = " MISSING_CLIENT_SECRETS_MESSAGE "
#ids = map(X['id']['videoId'], res['items'])
#list_captions(youtube, ids[0])
#cap_url = "http://video.google.com/timedtext?lang=en&v={0}"
##ids = ids[:1]
#res['snippet']['title']
#map(cap_url.format, ids)
#download_caption

#           search_videos = []
#       
#           # Merge video ids
#           for search_result in search_response.get("items", []):
#             search_videos.append(search_result["id"]["videoId"])
#           video_ids = ",".join(search_videos)
#       
#           # Call the videos.list method to retrieve location details for each video.
#           video_response = youtube.videos().list(
#             id=video_ids,
#             part='snippet, recordingDetails'
#           ).execute()

      
# Call the search.list method to retrieve results matching the specified
# query term.
def youtube_search(options):
  youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION,
    developerKey=DEVELOPER_KEY)

  # Call the search.list method to retrieve results matching the specified
  # query term.
  search_response = youtube.search().list(
    q=options.q,
    type="video",
    location=options.location,
    locationRadius=options.location_radius,
    part="id,snippet",
    maxResults=options.max_results
  ).execute()

  search_videos = []

  # Merge video ids
  for search_result in search_response.get("items", []):
    search_videos.append(search_result["id"]["videoId"])
  video_ids = ",".join(search_videos)

  # Call the videos.list method to retrieve location details for each video.
  video_response = youtube.videos().list(
    id=video_ids,
    part='snippet, recordingDetails'
  ).execute()

  videos = []

  # Add each result to the list, and then display the list of matching videos.
  for video_result in video_response.get("items", []):
    videos.append("%s, (%s,%s)" % (video_result["snippet"]["title"],
                              video_result["recordingDetails"]["location"]["latitude"],
                              video_result["recordingDetails"]["location"]["longitude"]))

  print "Videos:\n", "\n".join(videos), "\n"
'''
should_work = 'https://www.youtube.com/watch?v=shfjgRwNcWk'
http://stackoverflow.com/questions/9611397/how-to-extract-closed-caption-transcript-from-youtube-video
http://www.youtube.com/api/timedtext?v=zzfCVBSsvqA&lang=en
http://mo.dbxdb.com/Yang/
limit youtube search to deliberate closed-captions:
https://www.youtube.com/results?search_query=david+bowie%2C+cc
http://www.serpsite.com/youtube-subtitles-download-tool/
http://downsub.com/
http://code.google.com/p/gcap/
youtube-dl --write-auto-sub  --skip-download  https://www.youtube.com/watch?v=v9L4qL_XKcs
youtube-dl --list-subs http://www.youtube.com/watch?v=X6p5AZp7r_Q
just need to filter by youtube videos that have CC's already
'''

if __name__ == "__main__":
  argparser.add_argument("--q", help="Search term", default="Google")
  argparser.add_argument("--location", help="Location", default="37.42307,-122.08427")
  argparser.add_argument("--location-radius", help="Location radius", default="5km")
  argparser.add_argument("--max-results", help="Max results", default=25)
  args = argparser.parse_args() 
  try:
    youtube_search(args)
  except HttpError, e:
    print "An HTTP error %d occurred:\n%s" % (e.resp.status, e.content)
# The CLIENT_SECRETS_FILE variable specifies the name of a file that contains

# the OAuth 2.0 information for this application, including its client_id and
# client_secret. You can acquire an OAuth 2.0 client ID and client secret from
# the Google Developers Console at
# https://console.developers.google.com/.
# Please ensure that you have enabled the YouTube Data API for your project.
# For more information about using OAuth2 to access the YouTube Data API, see:
#   https://developers.google.com/youtube/v3/guides/authentication
CLIENT_SECRETS_FILE = "client_secrets.json"

YOUTUBE_READ_WRITE_SSL_SCOPE = "https://www.googleapis.com/auth/youtube.force-ssl"
YOUTUBE_API_SERVICE_NAME = "youtube"
YOUTUBE_API_VERSION = "v3"

# Authorize the request and store authorization credentials.
def get_authenticated_service(args):
  flow = flow_from_clientsecrets(CLIENT_SECRETS_FILE, scope=YOUTUBE_READ_WRITE_SSL_SCOPE,
    message=MISSING_CLIENT_SECRETS_MESSAGE)

  storage = Storage("%s-oauth2.json" % sys.argv[0])
  credentials = storage.get()

  if credentials is None or credentials.invalid:
    credentials = run_flow(flow, storage, args)

  # Trusted testers can download this discovery document from the developers page
  # and it should be in the same directory with the code.
  with open("youtube-v3-api-captions.json", "r") as f:
    doc = f.read()
    return build_from_document(doc, http=credentials.authorize(httplib2.Http()))

# Call the API's captions.list method to list the existing caption tracks.
def list_captions(youtube, video_id):
  results = youtube.captions().list(
    part="snippet",
    videoId=video_id
  ).execute()

  for item in results["items"]:
    id = item["id"]
    name = item["snippet"]["name"]
    language = item["snippet"]["language"]
    print "Caption track '%s(%s)' in '%s' language." % (name, id, language)

  return results["items"]


# Call the API's captions.insert method to upload a caption track in draft status.
def upload_caption(youtube, video_id, language, name, file):
  insert_result = youtube.captions().insert(
    part="snippet",
    body=dict(
      snippet=dict(
        videoId=video_id,
        language=language,
        name=name,
        isDraft=True
      )
    ),
    media_body=file
  ).execute()

  id = insert_result["id"]
  name = insert_result["snippet"]["name"]
  language = insert_result["snippet"]["language"]
  status = insert_result["snippet"]["status"]
  print "Uploaded caption track '%s(%s) in '%s' language, '%s' status." % (name,
      id, language, status)


# Call the API's captions.update method to update an existing caption track's draft status
# and publish it. If a new binary file is present, update the track with the file as well.
def update_caption(youtube, caption_id, file):
  update_result = youtube.captions().update(
    part="snippet",
    body=dict(
      id=caption_id,
      snippet=dict(
        isDraft=False
      )
    ),
    media_body=file
  ).execute()

  name = update_result["snippet"]["name"]
  isDraft = update_result["snippet"]["isDraft"]
  print "Updated caption track '%s' draft status to be: '%s'" % (name, isDraft)
  if file:
    print "and updated the track with the new uploaded file."


# Call the API's captions.download method to download an existing caption track.
def download_caption(youtube, caption_id, tfmt='srt'):
  subtitle = youtube.captions().download(
    id=caption_id,
    tfmt=tfmt
  ).execute()

  print "First line of caption track: %s" % (subtitle)

# Call the API's captions.delete method to delete an existing caption track.
def delete_caption(youtube, caption_id):
  youtube.captions().delete(
    id=caption_id
  ).execute()

  print "caption track '%s' deleted succesfully" % (caption_id)


if __name__ == "__main__":
  # The "videoid" option specifies the YouTube video ID that uniquely
  # identifies the video for which the caption track will be uploaded.
  argparser.add_argument("--videoid",
    help="Required; ID for video for which the caption track will be uploaded.")
  # The "name" option specifies the name of the caption trackto be used.
  argparser.add_argument("--name", help="Caption track name", default="YouTube for Developers")
  # The "file" option specifies the binary file to be uploaded as a caption track.
  argparser.add_argument("--file", help="Captions track file to upload")
  # The "language" option specifies the language of the caption track to be uploaded.
  argparser.add_argument("--language", help="Caption track language", default="en")
  # The "captionid" option specifies the ID of the caption track to be processed.
  argparser.add_argument("--captionid", help="Required; ID of the caption track to be processed")
  # The "action" option specifies the action to be processed.
  argparser.add_argument("--action", help="Action", default="all")


  args = argparser.parse_args()

  if (args.action in ('upload', 'list', 'all')):
    if not args.videoid:
          exit("Please specify videoid using the --videoid= parameter.")

  if (args.action in ('update', 'download', 'delete')):
    if not args.captionid:
          exit("Please specify captionid using the --captionid= parameter.")

#  if (args.action in ('upload', 'all')):
#    if not args.file:
#      exit("Please specify a caption track file using the --file= parameter.")
#    if not os.path.exists(args.file):
#      exit("Please specify a valid file using the --file= parameter.")

  youtube = get_authenticated_service(args)
  try:
    if args.action == 'upload':
      upload_caption(youtube, args.videoid, args.language, args.name, args.file)
    elif args.action == 'list':
      list_captions(youtube, args.videoid)
    elif args.action == 'update':
      update_caption(youtube, args.captionid, args.file);
    elif args.action == 'download':
      download_caption(youtube, args.captionid, 'srt')
    elif args.action == 'delete':
      delete_caption(youtube, args.captionid);
    else:
      # All the available methods are used in sequence just for the sake of an example.
      #upload_caption(youtube, args.videoid, args.language, args.name, args.file)
      captions = list_captions(youtube, args.videoid)

      if captions:
        first_caption_id = captions[0]['id'];
        #update_caption(youtube, first_caption_id, None);
        download_caption(youtube, first_caption_id, 'srt')
        #delete_caption(youtube, first_caption_id);
  except HttpError, e:
    print "An HTTP error %d occurred:\n%s" % (e.resp.status, e.content)
  else:
    print "Created and managed caption tracks."
