import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

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
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionService: ProductOptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
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
    save() {
        this.isSaving = true;
        if (this.productOption.id !== undefined) {
            this.productOptionService.update(this.productOption)
                .subscribe((res: ProductOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionService.create(this.productOption)
                .subscribe((res: ProductOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOption) {
        this.eventManager.broadcast({ name: 'productOptionModification', content: 'OK'});
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

    registerChangeInProductOptions() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', (response) => this.load(this.productOption.id));
    }
}
