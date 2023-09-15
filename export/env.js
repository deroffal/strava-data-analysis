function getToken() {
    let token = process.env['STRAVA_ACCESS_TOKEN']
    if (!token) {
        throw new Error("Please provide a value for env STRAVA_ACCESS_TOKEN !")
    }
    return token;
}

function getResourcesDirectory() {
    let resourcesDirectory = process.env['RESOURCES_DIRECTORY']
    if (!resourcesDirectory) {
        resourcesDirectory = 'data'
        console.warn(`No resources directory defined. Using default : ${resourcesDirectory}`)
    }
    return resourcesDirectory
}

module.exports = {getToken, getResourcesDirectory}
