from datetime import timedelta


def seconds_to_hhmmss(time: int):
    """
    Convert seconds to hours, minutes and seconds
    :time seconds: the number of seconds
    :return: a string with the number of hours, minutes and seconds (hh:mm:ss)
    """
    td = timedelta(seconds=time)

    days = td.days
    hours, remainder = divmod(td.seconds, 3600)
    minutes, seconds = divmod(remainder, 60)
    total_hours = str(days * 24 + hours).zfill(2)
    total_minutes = str(minutes).zfill(2)
    return f"{total_hours}:{total_minutes}:{seconds}"


def m_to_km(meters: int):
    """
    Convert meters to kilometers
    :param meters: the number of meters
    :return: the conversion (km,mm)
    """
    return f"{round(meters / 1000, 2)}km"
