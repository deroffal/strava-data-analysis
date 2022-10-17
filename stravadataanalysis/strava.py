def extract_segments(segments: dict):
    """
    Extract segment's data
    :param segments: segments
    :return: a list of segments :
    {
        "id": $segment['segment']['id'],
        "elapsedTime": $segment['elapsed_time'],
    }
    """
    datas = []
    for segment in segments:
        segment_id = segment['segment']['id']
        elapsed_time = segment['elapsed_time']
        segment_name = segment['name']
        datas.append({'id': segment_id, 'name': segment_name, 'elapsedTime': elapsed_time})
    return datas
