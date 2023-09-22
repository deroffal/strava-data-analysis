const mongoose = require('mongoose');

//region activitySchema
let activitySchema = new mongoose.Schema({
  "resource_state": {
    "type": "Number"
  },
  "athlete": {
    "id": {
      "type": "Number"
    },
    "resource_state": {
      "type": "Number"
    }
  },
  "name": {
    "type": "String"
  },
  "distance": {
    "type": "Number"
  },
  "moving_time": {
    "type": "Number"
  },
  "elapsed_time": {
    "type": "Number"
  },
  "total_elevation_gain": {
    "type": "Number"
  },
  "type": {
    "type": "String"
  },
  "sport_type": {
    "type": "String"
  },
  "workout_type": {
    "type": "Number"
  },
  "id": {
    "type": "Number"
  },
  "start_date": {
    "type": "Date"
  },
  "start_date_local": {
    "type": "Date"
  },
  "timezone": {
    "type": "String"
  },
  "utc_offset": {
    "type": "Number"
  },
  "location_city": {
    "type": "Mixed"
  },
  "location_state": {
    "type": "Mixed"
  },
  "location_country": {
    "type": "String"
  },
  "achievement_count": {
    "type": "Number"
  },
  "kudos_count": {
    "type": "Number"
  },
  "comment_count": {
    "type": "Number"
  },
  "athlete_count": {
    "type": "Number"
  },
  "photo_count": {
    "type": "Number"
  },
  "map": {
    "id": {
      "type": "String"
    },
    "polyline": {
      "type": "String"
    },
    "resource_state": {
      "type": "Number"
    },
    "summary_polyline": {
      "type": "String"
    }
  },
  "trainer": {
    "type": "Boolean"
  },
  "commute": {
    "type": "Boolean"
  },
  "manual": {
    "type": "Boolean"
  },
  "private": {
    "type": "Boolean"
  },
  "visibility": {
    "type": "String"
  },
  "flagged": {
    "type": "Boolean"
  },
  "gear_id": {
    "type": "Mixed"
  },
  "start_latlng": {
    "type": [
      "Number"
    ]
  },
  "end_latlng": {
    "type": [
      "Number"
    ]
  },
  "average_speed": {
    "type": "Number"
  },
  "max_speed": {
    "type": "Number"
  },
  "average_cadence": {
    "type": "Number"
  },
  "has_heartrate": {
    "type": "Boolean"
  },
  "average_heartrate": {
    "type": "Number"
  },
  "max_heartrate": {
    "type": "Number"
  },
  "heartrate_opt_out": {
    "type": "Boolean"
  },
  "display_hide_heartrate_option": {
    "type": "Boolean"
  },
  "elev_high": {
    "type": "Number"
  },
  "elev_low": {
    "type": "Number"
  },
  "upload_id": {
    "type": "Number"
  },
  "upload_id_str": {
    "type": "String"
  },
  "external_id": {
    "type": "String"
  },
  "from_accepted_tag": {
    "type": "Boolean"
  },
  "pr_count": {
    "type": "Number"
  },
  "total_photo_count": {
    "type": "Number"
  },
  "has_kudoed": {
    "type": "Boolean"
  },
  "description": {
    "type": "String"
  },
  "calories": {
    "type": "Number"
  },
  "perceived_exertion": {
    "type": "Mixed"
  },
  "prefer_perceived_exertion": {
    "type": "Boolean"
  },
  "segment_efforts": {
    "type": [
      "Mixed"
    ]
  },
  "splits_metric": {
    "type": [
      "Mixed"
    ]
  },
  "splits_standard": {
    "type": [
      "Mixed"
    ]
  },
  "laps": {
    "type": [
      "Mixed"
    ]
  },
  "best_efforts": {
    "type": [
      "Mixed"
    ]
  },
  "photos": {
    "primary": {
      "type": "Mixed"
    },
    "count": {
      "type": "Number"
    }
  },
  "stats_visibility": {
    "type": [
      "Mixed"
    ]
  },
  "hide_from_home": {
    "type": "Boolean"
  },
  "device_name": {
    "type": "String"
  },
  "embed_token": {
    "type": "String"
  },
  "private_note": {
    "type": "String"
  },
  "similar_activities": {
    "effort_count": {
      "type": "Number"
    },
    "average_speed": {
      "type": "Number"
    },
    "min_average_speed": {
      "type": "Number"
    },
    "mid_average_speed": {
      "type": "Number"
    },
    "max_average_speed": {
      "type": "Number"
    },
    "pr_rank": {
      "type": "Mixed"
    },
    "frequency_milestone": {
      "type": "Mixed"
    },
    "trend": {
      "speeds": {
        "type": [
          "Number"
        ]
      },
      "current_activity_index": {
        "type": "Number"
      },
      "min_speed": {
        "type": "Number"
      },
      "mid_speed": {
        "type": "Number"
      },
      "max_speed": {
        "type": "Number"
      },
      "direction": {
        "type": "Number"
      }
    },
    "resource_state": {
      "type": "Number"
    }
  },
  "available_zones": {
    "type": "Array"
  }
});
//endregion
const Activity = mongoose.model('Activity', activitySchema);

//region athleteActivitiesSchema
let athleteActivitiesSchema = new mongoose.Schema({
  "resource_state": {
    "type": "Number"
  },
  "athlete": {
    "id": {
      "type": "Number"
    },
    "resource_state": {
      "type": "Number"
    }
  },
  "name": {
    "type": "String"
  },
  "distance": {
    "type": "Number"
  },
  "moving_time": {
    "type": "Number"
  },
  "elapsed_time": {
    "type": "Number"
  },
  "total_elevation_gain": {
    "type": "Number"
  },
  "type": {
    "type": "String"
  },
  "sport_type": {
    "type": "String"
  },
  "workout_type": {
    "type": "Number"
  },
  "id": {
    "type": "Number"
  },
  "start_date": {
    "type": "Date"
  },
  "start_date_local": {
    "type": "Date"
  },
  "timezone": {
    "type": "String"
  },
  "utc_offset": {
    "type": "Number"
  },
  "location_city": {
    "type": "Mixed"
  },
  "location_state": {
    "type": "Mixed"
  },
  "location_country": {
    "type": "String"
  },
  "achievement_count": {
    "type": "Number"
  },
  "kudos_count": {
    "type": "Number"
  },
  "comment_count": {
    "type": "Number"
  },
  "athlete_count": {
    "type": "Number"
  },
  "photo_count": {
    "type": "Number"
  },
  "map": {
    "id": {
      "type": "String"
    },
    "summary_polyline": {
      "type": "String"
    },
    "resource_state": {
      "type": "Number"
    }
  },
  "trainer": {
    "type": "Boolean"
  },
  "commute": {
    "type": "Boolean"
  },
  "manual": {
    "type": "Boolean"
  },
  "private": {
    "type": "Boolean"
  },
  "visibility": {
    "type": "String"
  },
  "flagged": {
    "type": "Boolean"
  },
  "gear_id": {
    "type": "Mixed"
  },
  "start_latlng": {
    "type": [
      "Number"
    ]
  },
  "end_latlng": {
    "type": [
      "Number"
    ]
  },
  "average_speed": {
    "type": "Number"
  },
  "max_speed": {
    "type": "Number"
  },
  "average_cadence": {
    "type": "Number"
  },
  "has_heartrate": {
    "type": "Boolean"
  },
  "average_heartrate": {
    "type": "Number"
  },
  "max_heartrate": {
    "type": "Number"
  },
  "heartrate_opt_out": {
    "type": "Boolean"
  },
  "display_hide_heartrate_option": {
    "type": "Boolean"
  },
  "elev_high": {
    "type": "Number"
  },
  "elev_low": {
    "type": "Number"
  },
  "upload_id": {
    "type": "Number"
  },
  "upload_id_str": {
    "type": "String"
  },
  "external_id": {
    "type": "String"
  },
  "from_accepted_tag": {
    "type": "Boolean"
  },
  "pr_count": {
    "type": "Number"
  },
  "total_photo_count": {
    "type": "Number"
  },
  "has_kudoed": {
    "type": "Boolean"
  }
});
//endregion
const AthleteActivity = mongoose.model('AthleteActivity', athleteActivitiesSchema);

module.exports = {Activity, AthleteActivity}
