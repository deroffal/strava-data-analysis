import math

import pandas as pd


def round_down_to_nearest_100(num: int):
    """
    Round a distance to the nearest lower 100m
    :param num: distance (meters)
    :return: rounded distance
    """
    return math.floor(num / 100) * 100


def get_distance_distribution(activities: pd.DataFrame):
    distances = pd.DataFrame(activities['distance'].apply(round_down_to_nearest_100))
    distances_count = distances.groupby(['distance']).size() \
        .reset_index(name="count")

    distance_min = distances_count['distance'].min()
    distance_max = distances_count['distance'].max()

    empty_distances = []
    for dist in range(distance_min, distance_max, 100):
        if dist not in distances_count['distance']:
            empty_distances.append({'distance': dist, 'count': 0})

    distances_count = pd.concat([distances_count, pd.DataFrame(empty_distances)])
    distances_count.sort_values('distance', inplace=True)

    return distances_count
