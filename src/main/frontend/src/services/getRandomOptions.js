import {
  theme,
  eyesMap,
  eyebrowsMap,
  mouthsMap,
  hairMap,
  facialHairMap,
  clothingMap,
  accessoryMap,
  graphicsMap,
  hatMap,
  bodyMap,
} from '@bigheads/core'

function selectRandomKey(object) {
  return Object.keys(object)[
    Math.floor(Math.random() * Object.keys(object).length)
  ];
}

export function getRandomOptions(gender) {
  const skinTone = selectRandomKey(theme.colors.skin)
  const eyes = selectRandomKey(eyesMap)
  const eyebrows = selectRandomKey(eyebrowsMap)
  const mouth = selectRandomKey(mouthsMap)
  const hair = selectRandomKey(hairMap)
  let facialHair;
  const clothing = selectRandomKey(clothingMap)
  const accessory = selectRandomKey(accessoryMap)
  const graphic = selectRandomKey(graphicsMap)
  const hat = selectRandomKey(hatMap)
  let body;
  if (gender === 'male') {
    body = 'chest';
    facialHair = selectRandomKey(facialHairMap);
  } else if (gender === 'female') {
    body = 'breasts';
    facialHair = 'none';
  } else {
    body = selectRandomKey(bodyMap);
  }
  const hairColor = selectRandomKey(theme.colors.hair)
  const clothingColor = selectRandomKey(theme.colors.clothing)
  const circleColor = selectRandomKey(theme.colors.bgColors)
  const lipColor = selectRandomKey(theme.colors.lipColors)
  const hatColor = selectRandomKey(theme.colors.clothing)
  const faceMaskColor = selectRandomKey(theme.colors.clothing)

  const mask = true
  const faceMask = false
  const lashes = Math.random() > 0.5

  return {
    skinTone,
    eyes,
    eyebrows,
    mouth,
    hair,
    facialHair,
    clothing,
    accessory,
    graphic,
    hat,
    body,
    hairColor,
    clothingColor,
    circleColor,
    lipColor,
    hatColor,
    faceMaskColor,
    mask,
    faceMask,
    lashes
  }
}