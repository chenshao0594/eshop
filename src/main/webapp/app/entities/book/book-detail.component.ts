import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils, AlertService } from 'ng-jhipster';

import { Book } from './book.model';
import { BookService } from './book.service';

@Component({
    selector: 'jhi-book-detail',
    templateUrl: './book-detail.component.html'
})
export class BookDetailComponent implements OnInit, OnDestroy {

    book: Book;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private bookService: BookService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['book']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBooks();
    }

    load(id) {
        this.bookService.find(id).subscribe((book) => {
            this.book = book;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.book.id !== undefined) {
            this.bookService.update(this.book)
                .subscribe((res: Book) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.bookService.create(this.book)
                .subscribe((res: Book) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Book) {
        this.eventManager.broadcast({ name: 'bookModification', content: 'OK'});
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

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe('bookListModification', (response) => this.load(this.book.id));
    }
}
