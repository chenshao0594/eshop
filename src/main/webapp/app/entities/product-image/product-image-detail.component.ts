import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductImage } from './product-image.model';
import { ProductImageService } from './product-image.service';

@Component({
    selector: 'jhi-product-image-detail',
    templateUrl: './product-image-detail.component.html'
})
export class ProductImageDetailComponent implements OnInit, OnDestroy {

    productImage: ProductImage;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productImageService: ProductImageService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productImage']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductImages();
    }

    load(id) {
        this.productImageService.find(id).subscribe((productImage) => {
            this.productImage = productImage;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productImage.id !== undefined) {
            this.productImageService.update(this.productImage)
                .subscribe((res: ProductImage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productImageService.create(this.productImage)
                .subscribe((res: ProductImage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductImage) {
        this.eventManager.broadcast({ name: 'productImageModification', content: 'OK'});
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

    registerChangeInProductImages() {
        this.eventSubscriber = this.eventManager.subscribe('productImageListModification', (response) => this.load(this.productImage.id));
    }
}
