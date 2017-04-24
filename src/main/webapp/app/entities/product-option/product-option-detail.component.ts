import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ProductOption } from './product-option.model';
import { ProductOptionService } from './product-option.service';

@Component({
    selector: 'jhi-product-option-detail',
    templateUrl: './product-option-detail.component.html'
})
export class ProductOptionDetailComponent implements OnInit, OnDestroy {

    productOption: ProductOption;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionService: ProductOptionService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['productOption']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductOptions();
    }

    load(id) {
        this.productOptionService.find(id).subscribe((productOption) => {
            this.productOption = productOption;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductOptions() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', (response) => this.load(this.productOption.id));
    }
}
