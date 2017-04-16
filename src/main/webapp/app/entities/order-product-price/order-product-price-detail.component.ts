import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderProductPrice } from './order-product-price.model';
import { OrderProductPriceService } from './order-product-price.service';

@Component({
    selector: 'jhi-order-product-price-detail',
    templateUrl: './order-product-price-detail.component.html'
})
export class OrderProductPriceDetailComponent implements OnInit, OnDestroy {

    orderProductPrice: OrderProductPrice;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderProductPriceService: OrderProductPriceService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderProductPrice']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderProductPrices();
    }

    load(id) {
        this.orderProductPriceService.find(id).subscribe((orderProductPrice) => {
            this.orderProductPrice = orderProductPrice;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderProductPrice.id !== undefined) {
            this.orderProductPriceService.update(this.orderProductPrice)
                .subscribe((res: OrderProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductPriceService.create(this.orderProductPrice)
                .subscribe((res: OrderProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductPrice) {
        this.eventManager.broadcast({ name: 'orderProductPriceModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderProductPrices() {
        this.eventSubscriber = this.eventManager.subscribe('orderProductPriceListModification', (response) => this.load(this.orderProductPrice.id));
    }
}
