import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productPriceService: ProductPriceService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductPrices() {
        this.eventSubscriber = this.eventManager.subscribe('productPriceListModification', (response) => this.load(this.productPrice.id));
    }
}
