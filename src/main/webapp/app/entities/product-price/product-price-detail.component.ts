import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductPrice } from './product-price.model';
import { ProductPriceService } from './product-price.service';

@Component({
    selector: 'jhi-product-price-detail',
    templateUrl: './product-price-detail.component.html'
})
export class ProductPriceDetailComponent implements OnInit, OnDestroy {

    productPrice: ProductPrice;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productPriceService: ProductPriceService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productPrice', 'productPriceType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductPrices();
    }

    load(id) {
        this.productPriceService.find(id).subscribe((productPrice) => {
            this.productPrice = productPrice;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productPrice.id !== undefined) {
            this.productPriceService.update(this.productPrice)
                .subscribe((res: ProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productPriceService.create(this.productPrice)
                .subscribe((res: ProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductPrice) {
        this.eventManager.broadcast({ name: 'productPriceModification', content: 'OK'});
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

    registerChangeInProductPrices() {
        this.eventSubscriber = this.eventManager.subscribe('productPriceListModification', (response) => this.load(this.productPrice.id));
    }
}
