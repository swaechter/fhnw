class QuestionnairesRestService {
    set serverUrl(url) {
        this._serverUrl = url;
    }

    checkIfPropertiesAreSet() {
        if (!this._serverUrl) {
            throw Error('serverUrl not set');
        }
    }

    getAll() {
        this.checkIfPropertiesAreSet();
        return fetch(this._serverUrl).then(response =>
            // Convert to JSON questionnaires
            response.json()
        )
    }

    delete(id) {
        this.checkIfPropertiesAreSet();
        var request = new Request(this._serverUrl + '/' + id, {
            method: 'DELETE'
        });
        return fetch(request).then(response =>
            response.ok
        );
    }

    save(questionnaire) {
        this.checkIfPropertiesAreSet();
        var request = new Request(this._serverUrl, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(questionnaire)
        });
        return fetch(request).then(response =>
            response.json()
        )
    }

    update(questionnaire) {
        this.checkIfPropertiesAreSet();
        var request = new Request(this._serverUrl+ '/' + questionnaire.id, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(questionnaire)
        });
        return fetch(request).then(response =>
            response.ok
        )
    }
}

// Singleton pattern in ES6
export default new QuestionnairesRestService();
