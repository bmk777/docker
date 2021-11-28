from google.cloud import storage
import re
from google.cloud import dataproc_v1 as dataproc
from google.cloud import storage
import os

path = '.'
files = os.listdir(path)
for filename in files:
    print(filename)


#Verifying authentication
def implicit():


    # If you don't specify credentials when constructing the client, the
    # client library will look for credentials in the environment.
    storage_client = storage.Client()

    # Make an authenticated API request
    buckets = list(storage_client.list_buckets())
    print(buckets)
    
def explicit():
    from google.cloud import storage

    # Explicitly use service account credentials by specifying the private key
    # file.
    storage_client = storage.Client.from_service_account_json(
        'auth/finalproject-330320-a338574da62d.json')

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
            submit_job("finalProject-330320", "us-central1", "revisedcluster")
            
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

def submit_job(project_id, region, cluster_name):
    storage_client = storage.Client.from_service_account_json(
        'auth/finalproject-330320-a338574da62d.json')
    # Create the job client.
    job_client = dataproc.JobControllerClient(
        client_options={"api_endpoint": "{}-dataproc.googleapis.com:443".format(region)}
    )

    # Create the job config. 'main_jar_file_uri' can also be a
    # Google Cloud Storage URL.
    job = {
        "placement": {"cluster_name": cluster_name},
        "hadoop_job": {
            "main_class": "invertedindex",
            "jar_file_uris": ["gs://dataproc-staging-us-central1-102833165461-2apc7i7n/jar/invertedindex.jar"],
            "args": ["gs://dataproc-staging-us-central1-102833165461-2apc7i7n/Hugo/Miserables.txt", "gs://dataproc-staging-us-central1-102833165461-2apc7i7n/Hugo/codeFold" ],
        },
    }

    operation = job_client.submit_job_as_operation(
        request={"project_id": project_id, "region": region, "job": job}
    )
    response = operation.result()

    # Dataproc job output gets saved to the Google Cloud Storage bucket
    # allocated to the job. Use a regex to obtain the bucket and blob info.
    matches = re.match("gs://(.*?)/(.*)", response.driver_output_resource_uri)

    output = (
        storage.Client()
        .get_bucket(matches.group(1))
        .blob(f"{matches.group(2)}.000000000")
        .download_as_string()
    )

    print(f"Job finished successfully: {output}")

        
#implicit()
list_buckets()
explicit()
menu()
        



