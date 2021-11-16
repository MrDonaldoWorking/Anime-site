// PubSub system to listen and dispatch events from independent components
const eventBus = {
    // attachs an EventListener to the document object
    // callback will be called when the event gets fired
    on(event, callback) {
        document.addEventListener(event, (e) => callback(e.detail));
    },
    // fires an event using the CustomEvent API
    dispatch(event, data) {
        document.dispatchEvent(new CustomEvent(event, { detail: data }));
    },
    // removes the attached event from the document objcet
    remove(event, callback) {
        document.removeEventListener(event, callback);
    },
};
  
export default eventBus;
  