import json
from os import walk
from pathlib import Path


def list_files(directory: str):
    """
    List directory's files name
    :param directory:
    :return: Returns all files name in the directory
    """
    return next(walk(directory), (None, None, []))[2]


def read_json_files(path: str):
    """
    Read json files in a directory
    :param path:
    :return: the content of each file.
    """
    content = []
    for file_name in list_files(path):
        txt = Path(f"{path}/{file_name}").read_text(encoding='utf-8')
        data = json.loads(txt)
        content.append(data)

    return content
