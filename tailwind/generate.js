// npx tailwindcss build -o classes.css
const fs = require('fs');
const { execSync } = require("child_process");

// console.log('calling tailwindcss build process with all dependencies available');
// execSync("npx tailwindcss build -o classes.css");

const classesInOrder = [];
const omitCompoundClasses = [':not',':where', '>', '*', ','];
console.log('starting to write the final class');

fs.readFileSync('classes.css', 'utf-8').split('\n').forEach(line => {
	if(!line.startsWith('.')) {
		return;
	}

	let writeOut = true;

	omitCompoundClasses.forEach(classToOmit => {
		if(line.includes(classToOmit)) {
			writeOut = false;
			return;
		}
	});

	if(writeOut) {
		let finalClassName = line.replace(/\./g, '').replace(/{/g, '').replace(/}/g, '').replace(/\\/g, '').trim();
		if(finalClassName !== '') {
			classesInOrder.push(`${finalClassName}`);
		}
	}
})
console.log('writing final class list');
fs.writeFileSync('classes.txt', classesInOrder.join('\n'));