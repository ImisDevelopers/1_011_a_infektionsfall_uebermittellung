
const regexEscapables = /[.*+\-?^${}()|[\]\\]/g
function escapeRegExp(val: string): string {
  return val.replace(regexEscapables, '\\$&'); // $& means the whole matched string
}

/**
 * Options for TextMatcher.
 */
export interface TextMatcherOptions {
  caseInsensitive: boolean
  /**
   * Whether spaces and punctuation separate words that are
   * matched separately.
   */
  separateWords: boolean
  /**
   * Whether to calculate an additional score for matches; ideally, the higher
   * the score value, the better fitting the match.
   */
  withScore: boolean
}

export interface TextMatcherResult {
  matches: boolean
  score: number
}

/**
 * Advanced search text matching facility. Allows separate matching of
 * words and a naive match score calculation.
 */
export class TextMatcher {
  searchRegex: RegExp
  options: TextMatcherOptions

  constructor (search: string, options?: TextMatcherOptions) {
    // Retrieve option set to use
    this.options = {
      caseInsensitive: true,
      separateWords: true,
      withScore: false,
    }

    if (options) {
      Object.assign(this.options, options)
    }

    // Create a list of all search words to process
    let searchParts = [] as string[]
    if (this.options.separateWords) {
      search.split(/\s+/g).forEach(part => {
        if (part) {
          part.split(/[-+,.]/g).forEach(part => {
            if (part) searchParts.push(part)
          })
        }
      })
    } else {
      searchParts = [search]
    }

    // Construct RegExp used for matching
    let rawRegex = searchParts
      .map(entry => `(${escapeRegExp(entry)})`)
      .join('|')

    // This regex structure allows repeated matches
    rawRegex = `${rawRegex}(?:.*?(?:${rawRegex}))*`

    this.searchRegex = new RegExp(rawRegex, '' +
      (this.options.caseInsensitive ? 'i' : ''),
    )
  }

  /**
   * Tests the given text for a match with this matcher. If the option
   * `withScore` has been given during construction, the result will
   * also contain a naive score for the match. A higher score should in
   * general refer to a better match.
   */
  match (text: string): TextMatcherResult {
    const matchResults = this.searchRegex.exec(text)

    let score = 0
    if (matchResults && this.options.withScore) {
      // Count the number of groups that matched for score
      score = matchResults.reduce((score, match) => score + (match ? 1 : 0), 0)
    }

    return {
      matches: !!matchResults,
      score,
    }
  }
}
