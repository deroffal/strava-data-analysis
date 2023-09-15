const fsPromise = require("fs/promises")
const fs = require("fs")

const resourcesDirectory = '../.tmp'

async function readFile(path) {
    return fsPromise.readFile(`${resourcesDirectory}/${path}`)
        .then(content => JSON.parse(content.toString()))
        .catch(_ => [])
}

async function writeFile(jsons, fileName) {
    let directory = resourcesDirectory
    if (fileName.includes("/")) {
        directory = `${resourcesDirectory}/${fileName.substring(0, fileName.lastIndexOf("/"))}`
    }
    if (!fs.existsSync(directory)) {
        fs.mkdirSync(directory, {recursive: true})

    }
    let file = `${directory}/${fileName.substring(fileName.lastIndexOf("/") + 1)}`
    await fsPromise.writeFile(file, JSON.stringify(jsons), {flag: 'w'})
}

module.exports = {readFile, writeFile}
