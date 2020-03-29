/**
 * Contains information about the logged in user
 * @type {{getAuthDetailsFromToken(*): any, initAuthentication(): void, clearAuthentication(): void, user: ObjectConstructor, getAuthHeader(): ({Authorization: string}|{}), token: StringConstructor}}
 */
export const authenticationStore = {
    // To the person converting this to typescript:
    /*
        User Object is structured as follows:
        exp: 1585426165 (number)
        iat: 1585422565 (number)
        roles: Array [ "LABORATORY" ] (string[])
        sub: "labor" (string) = username
     */
    user: Object,
    token: String,

    /**
     * Parses the JWT from LocalStorage and fills the AuthenticationStore
     */
    initAuthentication() {
        const token = localStorage.getItem("token");
        if (token) {
            const user = this.getAuthDetailsFromToken(token);
            if (user.exp * 1000 > new Date().getTime()) {
                this.token = token;
                this.user = user;
            } else {
                console.log("Token expired. Removing.");
                localStorage.removeItem("token");
            }
        }
    },

    /**
     * Removes authentication from storage
     */
    clearAuthentication() {
        this.user = null;
        localStorage.removeItem("user");
    },

    /**
     * Parses the authentication token
     * @param jwt token
     * @return {any} user object; type definition see beginning of this file
     */
    getAuthDetailsFromToken(jwt) {
        const jwtData = jwt.split(".")[1];
        return JSON.parse(window.atob(jwtData));
    },

    /**
     * Sets the authentication according to the jwt
     * @param jwt token
     */
    setAuthentication(jwt) {
        localStorage.setItem("token", jwt);
        this.token = jwt;
        this.user = this.getAuthDetailsFromToken(jwt);
    },

    /**
     * Return header to be used in http requests against the backend
     * @return {{Authorization: string}|{}}
     */
    getAuthHeader() {
        if (this.token) {
            return {'Authorization': 'Bearer ' + this.token};
        } else {
            return {};
        }
    },

    /**
     * Checks whether the user has any of the required roles
     * @param roles
     * @return {boolean} true if user has any of the required roles
     */
    hasAnyRoleOf(roles) {
        if (!this.user.roles) {
            return false;
        }
        let hasRequiredRole = false;
        for (const requiredRole of roles) {
            if (this.user.roles.includes(requiredRole)) {
                hasRequiredRole = true;
                break;
            }
        }
        return hasRequiredRole;
    }
};
