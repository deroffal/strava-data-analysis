const fsPromise = require("fs/promises")
const fs = require("fs")

const resourcesDirectory = '../data/2022'

async function readFile(path) {
    return fsPromise.readFile(`${resourcesDirectory}/${path}`)
        .then(content => JSON.parse(content.toString()))
        .catch(_ => [])
}

async function writeFile(jsons, fileName) {
    if (!fs.existsSync(resourcesDirectory)) {
        fs.mkdirSync(resourcesDirectory, { recursive: true })
    }

    let file = `${resourcesDirectory}/${fileName}`
    await fsPromise.writeFile(file, JSON.stringify(jsons), { flag: 'w' })
}

module.exports = { readFile, writeToFile: writeFile }
