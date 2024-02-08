import json
from datetime import datetime
from os import walk
from pathlib import Path

import pandas as pd


def load_athlete_activities(file: str, year: int):
    """
    :param file: ths json file
    :param year: the year requested
    :return: athlete activities for one year data from a file as a Dataframe.
    """
    athlete_activities = pd.read_json(file, convert_dates=['start_date'])
    return athlete_activities.loc[(athlete_activities['start_date'] >= f'{year}-01-01') & (
                athlete_activities['start_date'] < f'{year + 1}-01-01')]


def load_activities(directory: str):
    files = _read_json_files(directory)
    activities = pd.DataFrame.from_dict(files)
    activities['start_time'] = activities['start_date_local'].apply(
        lambda x: datetime.strptime(x, "%Y-%m-%dT%H:%M:%SZ").time()
    )
    return activities


def _read_json_files(path: str):
    """
    Read json files in a directory
    :param path:
    :return: the content of each file.
    """
    content = []
    for file_name in _list_files(path):
        txt = Path(f"{path}/{file_name}").read_text(encoding='utf-8')
        data = json.loads(txt)
        content.append(data)

    return content


def _list_files(directory: str):
    """
    List directory's files name
    :param directory:
    :return: Returns all files name in the directory
    """
    return next(walk(directory), (None, None, []))[2]
