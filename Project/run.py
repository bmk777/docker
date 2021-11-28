from google.cloud import storage
import re
from google.cloud import dataproc_v1 as dataproc
from google.cloud import storage


#Verifying authentication
def implicit():


    # If you don't specify credentials when constructing the client, the
    # client library will look for credentials in the environment.
    storage_client = storage.Client()

    # Make an authenticated API request
    buckets = list(storage_client.list_buckets())
    print(buckets)

#bucket test display
def list_buckets():
    """Lists all buckets."""

    storage_client = storage.Client()
    buckets = storage_client.list_buckets()

    for bucket in buckets:
        print(bucket.name)

def menu():
    #  Display the menu
    list_buckets()
    print("1\t Locate a file")
    print("2\t Search word")
    # Get user's choice
    choice = 1
    location = ""
    while (choice != -1) :
        print("Please enter your choice:")
        choice = input()
        # Display the title of the chosen module
        if (choice=="1"):
            print("Locate a file")
            print("Enter the location of the directory you would like to interact with")
            print("E.g C:/users/ect/...")
            # consume debrees
            location = input()
            print("You entered " + location)
            upload_blob("dataproc-staging-us-central1-102833165461-2apc7i7n", location, "revisedcluster")
            
        if choice=="2":
            print("Search word")
            # System.out.println("Please enter your word choice:");
            # word = in.nextLine();

            print("Invalid choice")
            
def upload_blob(bucket_name, source_file_name, destination_blob_name):
    """Uploads a file to the bucket."""
    # The ID of your GCS bucket
    # bucket_name = "your-bucket-name"
    # The path to your file to upload
    # source_file_name = "local/path/to/file"
    # The ID of your GCS object
    # destination_blob_name = "storage-object-name"

    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)

    blob.upload_from_filename(source_file_name)

    print(
        "File {} uploaded to {}.".format(
            source_file_name, destination_blob_name
        )
    )

        
        
implicit()
list_buckets()
menu()
        



