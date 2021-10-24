import boto3

#2
s3 = boto3.resource('s3', 
    aws_access_key_id='AKIAQUE443DX22KJR6E6', 
    aws_secret_access_key='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' 
) 

#3
try: 
    s3.create_bucket(Bucket='brendansbigbuckets', CreateBucketConfiguration={ 
        'LocationConstraint': 'us-west-2'}) 
except Exception as e: 
    print (e) 

#4
bucket = s3.Bucket("brendansbigbuckets") 
bucket.Acl().put(ACL='public-read')

#9
body = open('test.jpg', 'rb')

#10
o = s3.Object('brendansbigbuckets', 'test').put(Body=body )\

#12
s3.Object('brendansbigbuckets', 'test').Acl().put(ACL='public-read')

#13
dyndb = boto3.resource('dynamodb', 
    region_name='us-west-2', 
    aws_access_key_id='AKIAQUE443DX22KJR6E6', 
    aws_secret_access_key='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' 
 )

#16
try: 
    table = dyndb.create_table( 
        TableName='DataTable', 
        KeySchema=[ 
            { 
                'AttributeName': 'PartitionKey', 
                'KeyType': 'HASH' 
            }, 
            { 
                'AttributeName': 'RowKey', 
                'KeyType': 'RANGE' 
            } 
        ], 
        AttributeDefinitions=[ 
            { 
                'AttributeName': 'PartitionKey', 
                'AttributeType': 'S' 
            }, 
            { 
                'AttributeName': 'RowKey', 
                'AttributeType': 'S' 
            }, 
 
        ], 
        ProvisionedThroughput={ 
            'ReadCapacityUnits': 5, 
            'WriteCapacityUnits': 5 
        } 
    ) 
except Exception as e: 
    print (e) 
    #if there is an exception, the table may already exist.   if so... 
    table = dyndb.Table("DataTable")

#17
table.meta.client.get_waiter('table_exists').wait(TableName='DataTable')

#18
print(table.item_count)

import csv
urlbase = "https://s3-us-west-2.amazonaws.com/brendansbigbuckets/"
with open('datafiles\experiments.csv', 'rb') as csvfile: 
    csvf = csv.reader(csvfile, delimiter=',', quotechar='|') 
    for item in csvf: 
        print(item) 
        body = open('datafiles\\'+item[3], 'rb') 
        s3.Object('brendansbigbuckets', item[3]).put(Body=body ) 
        md = s3.Object('brendansbigbuckets', item[3]).Acl().put(ACL='public-read') 
         
        url = urlbase+item[3] 
        metadata_item = {'PartitionKey': item[0], 'RowKey': item[1],  
                 'description' : item[4], 'date' : item[2], 'url':url}  
        try: 
            table.put_item(Item=metadata_item) 
        except: 
            print("item may already be there or another failure") 