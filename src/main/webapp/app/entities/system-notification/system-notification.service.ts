import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SystemNotification } from './system-notification.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class SystemNotificationService {

    private resourceUrl = 'api/system-notifications';
    private resourceSearchUrl = 'api/system-notifications/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(systemNotification: SystemNotification): Observable<SystemNotification> {
        const copy: SystemNotification = Object.assign({}, systemNotification);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(systemNotification.endDate);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(systemNotification.startDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(systemNotification: SystemNotification): Observable<SystemNotification> {
        const copy: SystemNotification = Object.assign({}, systemNotification);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(systemNotification.endDate);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(systemNotification.startDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SystemNotification> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.endDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.endDate);
            jsonResponse.startDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.startDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].endDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].endDate);
            jsonResponse[i].startDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].startDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
