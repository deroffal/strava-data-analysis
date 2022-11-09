import matplotlib.pyplot as plt

from stravadataanalysis.src.domain import data_conversion


def plot_split_metric_heartrate(metrics):
    fig, ax = plt.subplots()
    ax.scatter(metrics['split'], metrics['average_heartrate'])

    ax.set_xlabel("km split")
    ax.set_ylabel("heart rate (bpm)")
    plt.show()


def plot_split_metric_speed(metrics):
    fig, ax = plt.subplots()
    metrics['average_speed_kmh'] = metrics['average_speed'].apply(data_conversion.mps_to_kmh)
    ax.scatter(metrics['split'], metrics['average_speed_kmh'])

    ax.set_xlabel("km split")
    ax.set_ylabel("speed (km/h)")
