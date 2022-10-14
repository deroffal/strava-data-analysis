const fsPromise = require("fs/promises")
const fs = require("fs")

const resourcesDirectory = '../data/2022'

async function readFile(path) {
    let content = await fsPromise.readFile(`${resourcesDirectory}/${path}`)
    return JSON.parse(content.toString())
}

async function writeFile(jsons, fileName) {
    if (!fs.existsSync(resourcesDirectory)) {
        fs.mkdirSync(resourcesDirectory, {recursive: true})
    }

    let file = `${resourcesDirectory}/${fileName}`
    await fsPromise.writeFile(file, JSON.stringify(jsons), {flag: 'w'})
}

module.exports = {readFile, writeToFile: writeFile}
