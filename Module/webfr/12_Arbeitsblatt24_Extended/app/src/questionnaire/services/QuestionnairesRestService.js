class QuestionnairesRestService {
    set serverUrl(url) {
        this._serverUrl = url;
    }

    checkIfPropertiesAreSet() {
        if (!this._serverUrl) {
            throw Error('serverUrl not set');
        }
    }

    /**
     * Returns all questionnaires as array of questionnaire objects
     */
    async getAll() {
        this.checkIfPropertiesAreSet();
        const response = await fetch(this._serverUrl);
        // Convert to JSON questionnaires
        return response.json()
    }

    /**
     * Deletes given questionnaire.
     *
     * @param id id of questionnaire to delete
     */
    async delete(id) {
        this.checkIfPropertiesAreSet();
        const request = new Request(this._serverUrl + '/' + id, {
            method: 'DELETE'
        });
        const response = await fetch(request);
        return response.ok;
    }

    /**
     * Saves given questionnaire.
     *
     * @param {*} questionnaire questionnaire to save
     */
    async save(questionnaire) {
        this.checkIfPropertiesAreSet();
        const request = new Request(this._serverUrl, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(questionnaire)
        });
        const response = await fetch(request);
        return response.json()
    }

    /**
     * Updates given questionnaire.
     *
     * @param {questionnaire} questionnaire questionnaire to update
     */
    async update(questionnaire) {
        this.checkIfPropertiesAreSet();
        const request = new Request(this._serverUrl + '/' + questionnaire.id, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(questionnaire)
        });
        const response = await fetch(request);
        return response.ok
    }
}

// Singleton pattern in ES6
export default new QuestionnairesRestService();
