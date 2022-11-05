const { readFile, writeToFile } = require("./file-adatpter");

async function getLastRecordedActivityDate() {
    let currentAthleteActivities = await readFile('/athlete_activities.json')
    if (currentAthleteActivities.length != 0) {
        return new Date(currentAthleteActivities[currentAthleteActivities.length - 1]['start_date'])
    } else {
        return new Date(new Date().getFullYear(), 0, 1)
    }

}

async function addNewActivitiesToAthleteActivities(newActivities) {
    let activities = await readFile('/athlete_activities.json')
    activities.push(...newActivities);
    await writeToFile(activities, '/athlete_activities.json')
}

async function addNewActivity(newActivity) {
    await writeToFile(newActivity, `/activities/${newActivity.id}`)
}

module.exports = { getLastRecordedActivityDate, addNewActivitiesToAthleteActivities, addNewActivity }
