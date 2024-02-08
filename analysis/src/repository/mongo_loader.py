import pandas as pd
from pymongo import MongoClient


def read_mongo(db, collection, query={}, host='localhost', port=27017, username=None, password=None, no_id=True):

    mongo_uri = 'mongodb://%s:%s@%s:%s/%s' % (username, password, host, port, db)
    print(mongo_uri)
    conn = MongoClient(mongo_uri)
    db = conn[db]

    cursor = db[collection].find(query)

    df = pd.DataFrame(list(cursor))

    # Delete the _id
    if no_id:
        del df['_id']

    return df
