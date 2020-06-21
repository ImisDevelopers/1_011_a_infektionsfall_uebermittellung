const regexEscapables = /[.*+\-?^${}()|[\]\\]/g
function escapeRegExp(val: string): string {
  return val.replace(regexEscapables, '\\$&') // $& means the whole matched string
}

/**
 * Options for TextMatcher.
 */
export interface TextMatcherOptions {
  /**
   * Whether only a single word is required for a match. Only effective if used
   * together with `separateWords` option.
   */
  anyMatches: boolean
  /**
   * Whether to match ignoring casing.
   */
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
  // All regexps in this array need to match for an overall match
  searchRegex: RegExp[]
  options: TextMatcherOptions

  constructor(search: string, options?: Partial<TextMatcherOptions>) {
    // Retrieve option set to use
    this.options = {
      anyMatches: false,
      caseInsensitive: true,
      separateWords: true,
      withScore: false,
    }

    if (options) {
      Object.assign(this.options, options)
    }

    let patterns = [] as string[][]
    if (!this.options.separateWords) {
      patterns = [[search]]
    } else {
      // Create a list of all search words to process
      const searchParts = [] as string[]

      search.split(/\s+|[-+,.]/g).forEach((part) => {
        if (part) searchParts.push(part)
      })

      if (this.options.anyMatches) {
        // Single regexp, matching any word
        patterns.push(searchParts)
      } else {
        // One regexp for each word
        patterns = searchParts.map((part) => [part])
      }
    }

    // Construct RegExps used for matching
    this.searchRegex = patterns.map((searchParts) => {
      let rawRegex = searchParts
        .map((entry) => `(${escapeRegExp(entry)})`)
        .join('|')

      // This regex structure allows repeated matches
      rawRegex = `${rawRegex}(?:.*?(?:${rawRegex}))*`

      return new RegExp(
        rawRegex,
        '' + (this.options.caseInsensitive ? 'i' : '')
      )
    })
  }

  /**
   * Tests the given text for a match with this matcher. If the option
   * `withScore` has been given during construction, the result will
   * also contain a naive score for the match. A higher score should in
   * general refer to a better match.
   */
  match(text: string): TextMatcherResult {
    return this.searchRegex
      .map((re) => re.exec(text))
      .map((matchResults) => {
        let score = 0
        if (matchResults && this.options.withScore) {
          // Count the number of groups that matched for score
          score = matchResults.reduce(
            (score, match) => score + (match ? 1 : 0),
            0
          )
        }

        return {
          matches: !!matchResults,
          score,
        }
      })
      .reduce(
        (result, matchResult) => ({
          matches: result.matches && matchResult.matches,
          score: result.score + matchResult.score,
        }),
        { matches: true, score: 0 }
      )
  }
}
