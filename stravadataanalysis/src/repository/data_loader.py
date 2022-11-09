from datetime import datetime

import pandas as pd

from stravadataanalysis.src.repository import file_utils


def load_athlete_activities(file: str, year: int):
    """
    :param file: ths json file
    :param year: the year requested
    :return: athlete activities for one year data from a file as a Dataframe.
    """
    athlete_activities = pd.read_json(file, convert_dates=['start_date'])
    return athlete_activities.loc[(athlete_activities['start_date'] >= f'{year}-01-01') & (athlete_activities['start_date'] < f'{year + 1}-01-01')]


def load_activities(directory: str):
    files = file_utils.read_json_files(directory)
    activities = pd.DataFrame.from_dict(files)
    activities['start_time'] = activities['start_date_local'].apply(lambda x: datetime.strptime(x, "%Y-%m-%dT%H:%M:%SZ").time())
    return activities
