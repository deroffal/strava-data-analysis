from pandas import DataFrame


def get_overview(activities: DataFrame):
    overview_2022 = activities[['type', 'distance', 'elapsed_time']] \
        .groupby('type') \
        .agg({'distance': ['count', 'sum', 'mean'], 'elapsed_time': ['sum', 'mean']})

    distance_df = overview_2022["distance"]
    elapsed_time_df = overview_2022["elapsed_time"]

    overview = distance_df.merge(elapsed_time_df, on='type', suffixes=("_distance", "_elapsed_time"))

    return overview
