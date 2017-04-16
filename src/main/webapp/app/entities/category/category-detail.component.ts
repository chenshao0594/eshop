import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Category } from './category.model';
import { CategoryService } from './category.service';

@Component({
    selector: 'jhi-category-detail',
    templateUrl: './category-detail.component.html'
})
export class CategoryDetailComponent implements OnInit, OnDestroy {

    category: Category;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private categoryService: CategoryService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['category']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategories();
    }

    load(id) {
        this.categoryService.find(id).subscribe((category) => {
            this.category = category;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.category.id !== undefined) {
            this.categoryService.update(this.category)
                .subscribe((res: Category) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.categoryService.create(this.category)
                .subscribe((res: Category) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Category) {
        this.eventManager.broadcast({ name: 'categoryModification', content: 'OK'});
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

    registerChangeInCategories() {
        this.eventSubscriber = this.eventManager.subscribe('categoryListModification', (response) => this.load(this.category.id));
    }
}
