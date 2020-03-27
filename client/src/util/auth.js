/**
 * Contains information about the logged in user
 * @type {{user: null}}
 */
export const authenticationStore = {
    user: null,

    /**
     * Parses the JWT from LocalStorage and fills the AuthenticationStore
     */
    initAuthentication() {
        const user = localStorage.getItem("user");
        if (user) {
            authenticationStore.user = user;
        }
    },

    /**
     * Removes authentication from storage
     */
    clearAuthentication() {
        authenticationStore.user = null;
        localStorage.removeItem("user");
    }
};

/**
 * Return header to be used in http requests against the backend
 * @return {{Authorization: string}|{}}
 */
export function getAuthHeader() {
    // return authorization header with jwt token
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        return {'Authorization': 'Bearer ' + user.token};
    } else {
        return {};
    }
}
