import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private bookService: BookService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe('bookListModification', (response) => this.load(this.book.id));
    }
}
