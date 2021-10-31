To run the client application

docker pull bmk77/workingproject:1
docker run -it workingproject:1
Follow the on screen instructions to continue

Plan to connect to GCP
1.Create a gcloud iam service
2. grant perisionss to the service account with "gcloud projects add-iam-policy-binding PROJECT_ID --member="serviceAccount:NAME@PROJECT_ID.iam.gserviceaccount.com" --role="roles/owner""
3. Generate json authentication file
4. Add below code to verify we are connected and show a bucket we have available.

For file location
	display data held in the current buckets after authentication
	User selects case one of the menu.
	//Verify authentication.
	//Print out command to access file location

For word
	User selects case two of the menu
	//Check the user is authenticated
	//navigate to file location from previous step
	//implement sort/find scheme (inverted)
	//return data: Frequence, ect..
