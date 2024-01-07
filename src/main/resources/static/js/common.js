let commonMethod = {
    fetchGet: async function (url, params) {
        if (params != null) {
            let query = Object.keys(params)
                .filter(k => params[k] != null)
                .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
                .join('&');
            url = url + "?" + query;
        }

        return await this.fetchData(url);
    },

    fetchPost: async function (url, body) {
        let option = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }

        return await this.fetchData(url, option);
    },

    fetchPut: async function (url, body) {
        let option = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }

        return await this.fetchData(url, option);
    },

    fetchDelete: async function (url, body) {
        let option = {
            method: "Delete",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }

        return await this.fetchData(url, option);
    },

    fetchData: async function (url, option) {
        const res = await fetch(this.getApiUrl(url), option);

        const contentType = res.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            const data = await res.json();
            if (res.ok) {
             return data;
            }
            drawError(data);
        } else {
            return null;
        }
    },

    clearError: function () {
        $(".err").empty();
    },

    drawError: function (error) {
        $(".err").text(error.message);
    },

    getApiUrl: function (url) {
        return "/api" + url;
    }
}

