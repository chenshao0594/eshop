import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { OrderProductPrice } from './order-product-price.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class OrderProductPriceService {

    private resourceUrl = 'api/order-product-prices';
    private resourceSearchUrl = 'api/order-product-prices/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(orderProductPrice: OrderProductPrice): Observable<OrderProductPrice> {
        const copy: OrderProductPrice = Object.assign({}, orderProductPrice);
        copy.productPriceSpecialStartDate = this.dateUtils
            .convertLocalDateToServer(orderProductPrice.productPriceSpecialStartDate);
        copy.productPriceSpecialEndDate = this.dateUtils
            .convertLocalDateToServer(orderProductPrice.productPriceSpecialEndDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(orderProductPrice: OrderProductPrice): Observable<OrderProductPrice> {
        const copy: OrderProductPrice = Object.assign({}, orderProductPrice);
        copy.productPriceSpecialStartDate = this.dateUtils
            .convertLocalDateToServer(orderProductPrice.productPriceSpecialStartDate);
        copy.productPriceSpecialEndDate = this.dateUtils
            .convertLocalDateToServer(orderProductPrice.productPriceSpecialEndDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<OrderProductPrice> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.productPriceSpecialStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.productPriceSpecialStartDate);
            jsonResponse.productPriceSpecialEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.productPriceSpecialEndDate);
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
            jsonResponse[i].productPriceSpecialStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].productPriceSpecialStartDate);
            jsonResponse[i].productPriceSpecialEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].productPriceSpecialEndDate);
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
