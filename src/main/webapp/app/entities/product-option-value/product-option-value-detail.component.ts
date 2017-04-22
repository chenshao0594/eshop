import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ProductOptionValue } from './product-option-value.model';
import { ProductOptionValueService } from './product-option-value.service';

@Component({
    selector: 'jhi-product-option-value-detail',
    templateUrl: './product-option-value-detail.component.html'
})
export class ProductOptionValueDetailComponent implements OnInit, OnDestroy {

    productOptionValue: ProductOptionValue;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionValueService: ProductOptionValueService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['productOptionValue']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductOptionValues();
    }

    load(id) {
        this.productOptionValueService.find(id).subscribe((productOptionValue) => {
            this.productOptionValue = productOptionValue;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductOptionValues() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionValueListModification', (response) => this.load(this.productOptionValue.id));
    }
}
